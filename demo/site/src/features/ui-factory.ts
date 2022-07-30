import {
  getViewDescription, PreloaderHandler, registry, ViewReference,
} from 'elsa-web-core';
import { reactElementWrapperFactory, ReactElementWrapper } from './ui-common';

// eslint-disable-next-line import/prefer-default-export
export const uiFactory = {
  // eslint-disable-next-line max-len
  createElementForId: async <T extends ReactElementWrapper>(id: string, ph:PreloaderHandler|null = null,
    operationId:string|null = null) => {
    const description = (await getViewDescription(id, ph, operationId));
    const factory = registry.get(reactElementWrapperFactory, description.view.name)!!;
    return (await factory.createWrapper(description)) as T;
  },
  // eslint-disable-next-line max-len
  createElement: async <T extends ReactElementWrapper>(ref: ViewReference<T>, ph:PreloaderHandler|null = null,
    operationId:string|null = null) => uiFactory.createElementForId(ref.id, ph, operationId),
};
