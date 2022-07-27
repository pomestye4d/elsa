import { ReactElement } from 'react';
import { RegistryItem, RegistryItemType, ViewDescription } from 'elsa-web-core';

export interface ReactElementWrapper {
    createReactElement: () => ReactElement;
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
