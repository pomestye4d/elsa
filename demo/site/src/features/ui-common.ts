// eslint-disable-next-line max-classes-per-file
import React, { ReactNode } from 'react';
import {
  generateUUID, RegistryItem, RegistryItemType, ViewDescription, ViewReference,
} from 'elsa-web-core';

export interface ReactElementWrapper {
    createReactElement: () => ReactNode;
}

export interface ReactElementWrapperFactory {
    // eslint-disable-next-line no-unused-vars
    createWrapper: (description: ViewDescription) => Promise<ReactElementWrapper>
}

export const reactElementWrapperFactory = new RegistryItemType<ReactElementWrapperFactory>('react-element-wrapper-factory');

// eslint-disable-next-line max-len
export abstract class BaseReactElementWrapperFactory implements ReactElementWrapperFactory, RegistryItem<ReactElementWrapperFactory> {
    abstract getId(): string;

    // eslint-disable-next-line no-unused-vars
    abstract createWrapper(description: ViewDescription): Promise<ReactElementWrapper>;

    getType(): RegistryItemType<ReactElementWrapperFactory> {
      return reactElementWrapperFactory;
    }
}

export interface ToolHandler<T> {
    initialize: () => Promise<void>,
    getTitle: () => string,
    // eslint-disable-next-line no-unused-vars
    onClick: (view:T) => Promise<void>
    getPriority(): number
}

export const toolHandlersRegistryItemType = new RegistryItemType<ToolHandler<any>>('tool-handlers');

export abstract class BaseToolHandler<T> implements RegistryItem<ToolHandler<T>>, ToolHandler<T> {
  getId() {
    return generateUUID();
  }

  getType() {
    return toolHandlersRegistryItemType;
  }

    abstract getTitle(): string;

    // eslint-disable-next-line no-empty-function
    async initialize() {}

    // eslint-disable-next-line no-unused-vars
    abstract onClick(view: T): Promise<void>;

    abstract getPriority(): number;
}

export type ReactProxyViewProperties = {
    // eslint-disable-next-line react/no-unused-prop-types
    ref:React.Ref<any>,
    callbackId: number
}

export type ReactComponentCallback = {
    render: () => ReactNode
}

export const reactComponentsCallbacks = new Map<number, ReactComponentCallback>();

// eslint-disable-next-line react/prefer-stateless-function,no-unused-vars
export class ReactProxyView extends React.Component<ReactProxyViewProperties, void> {
    private readonly callbackId: number

    private mounted:boolean = false;

    constructor(props:ReactProxyViewProperties) {
      super(props);
      this.callbackId = props.callbackId;
    }

    componentDidMount() {
      this.mounted = true;
    }

    componentWillUnmount() {
      this.mounted = false;
    }

    // eslint-disable-next-line react/no-unused-class-component-methods
    isMounted() {
      return this.mounted;
    }

    render() {
      return reactComponentsCallbacks.get(this.callbackId)!.render();
    }
}
let currentCalbackId = 0;
export const getNextCallbackId = () => {
  currentCalbackId += 1;
  return currentCalbackId;
};

export interface ViewInterceptor<T> {
    getViewRef(): ViewReference<any>,
    // eslint-disable-next-line no-unused-vars
    afterInitialize: (view:T) => Promise<void>,
    getPriority(): number
}

export class ViewInterceptorsRegistry {
  private interceptors = new Map<string, ViewInterceptor<any>[]>();

  register(interceptor: ViewInterceptor<any>) {
    let values = this.interceptors.get(interceptor.getViewRef().id);
    if (!values) {
      values = [];
      this.interceptors.set(interceptor.getViewRef().id, values);
    }
    values.push(interceptor);
    values.sort((a, b) => a.getPriority() - b.getPriority());
  }

  getForViewId(viewId: string) {
    return this.interceptors.get(viewId) || [];
  }
}

export const viewInterceptorsRegistry = new ViewInterceptorsRegistry();
