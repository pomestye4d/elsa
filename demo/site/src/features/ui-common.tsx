// eslint-disable-next-line max-classes-per-file,no-use-before-define
import React, { ReactNode } from 'react';
import {
  // eslint-disable-next-line max-len
  generateUUID, PreloaderHandler, RegistryItem, RegistryItemType, ViewDescription, ViewReference, XmlNode,
} from 'elsa-web-core';
import { Modal } from 'antd';

export interface ReactElementWrapper {
    createReactElement: () => ReactNode;
}

export interface ReactViewFactory {
    // eslint-disable-next-line no-unused-vars,max-len
    createWrapper: (description: ViewDescription, ph:PreloaderHandler|null, operationId:string|null) => Promise<ReactElementWrapper>
}

export interface ReactWidgetFactory {
    // eslint-disable-next-line no-unused-vars,max-len
    createWrapper: (description: XmlNode, ph:PreloaderHandler|null, operationId:string|null,) => Promise<ReactElementWrapper>
}

export const reactViewFactory = new RegistryItemType<ReactViewFactory>('react-view-factory');

export const reactWidgetFactory = new RegistryItemType<ReactWidgetFactory>('react-widget-factory');

// eslint-disable-next-line max-len
export abstract class BaseViewFactory implements ReactViewFactory, RegistryItem<ReactViewFactory> {
    abstract getId(): string;

    // eslint-disable-next-line no-unused-vars,max-len
    abstract createWrapper(description: ViewDescription, ph:PreloaderHandler|null, operationId:string|null): Promise<ReactElementWrapper>;

    getType(): RegistryItemType<ReactViewFactory> {
      return reactViewFactory;
    }
}

// eslint-disable-next-line max-len
export abstract class BaseWidgetFactory implements ReactWidgetFactory, RegistryItem<ReactWidgetFactory> {
    abstract getId(): string;

    // eslint-disable-next-line no-unused-vars,max-len
    abstract createWrapper(description: XmlNode, ph:PreloaderHandler|null, operationId:string|null): Promise<ReactElementWrapper>;

    getType(): RegistryItemType<ReactWidgetFactory> {
      return reactWidgetFactory;
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
    render?: () => ReactNode
    dialogElementWrapper?: ReactElementWrapper,
    // eslint-disable-next-line no-unused-vars
    dialogCallback?: (dialog: any) => Promise<void>,
    // eslint-disable-next-line no-unused-vars
    onDidMount?: (comp: any)=>void
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
      // eslint-disable-next-line max-len,no-unused-expressions
      reactComponentsCallbacks.get(this.callbackId)!.onDidMount && reactComponentsCallbacks.get(this.callbackId)!.onDidMount!(this);
    }

    componentWillUnmount() {
      this.mounted = false;
    }

    // eslint-disable-next-line react/no-unused-class-component-methods
    isMounted() {
      return this.mounted;
    }

    render() {
      return reactComponentsCallbacks.get(this.callbackId)!.render!();
    }
}
let currentCallbackId = 0;
export const getNextCallbackId = () => {
  currentCallbackId += 1;
  return currentCallbackId;
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

export interface Dialog<V extends ReactElementWrapper> {
    getView(): V,
    close(): void
}

type ReactDialogProxyProps = {
    title: string,
    callbackId: number
}

type ReactDialogProxyState = {
    visible: boolean
}

// eslint-disable-next-line react/prefer-stateless-function,no-unused-vars,max-len
export class ReactDialogProxy<V extends ReactElementWrapper> extends React.Component<ReactDialogProxyProps, ReactDialogProxyState> implements Dialog<V> {
    private readonly callbackId: number

    constructor(props:ReactDialogProxyProps) {
      super(props);
      this.callbackId = props.callbackId;
      this.state = {
        visible: true,
      };
    }

    render() {
      const { title } = this.props;
      const { visible } = this.state;
      return (
        <Modal
          visible={visible}
          title={title}
          onCancel={() => { this.setState({ visible: false }); }}
          onOk={() => {
              reactComponentsCallbacks.get(this.callbackId)!.dialogCallback!(this);
          }}
        >
          {/* eslint-disable-next-line max-len */}
          {reactComponentsCallbacks.get(this.callbackId)!.dialogElementWrapper!.createReactElement()}
        </Modal>
      );
    }

    // eslint-disable-next-line react/no-unused-class-component-methods,react/sort-comp
    close(): void {
      this.setState({ visible: false });
    }

    // eslint-disable-next-line react/no-unused-class-component-methods
    getView(): V {
      return reactComponentsCallbacks.get(this.callbackId)!.dialogElementWrapper as V;
    }
}
