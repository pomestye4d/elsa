import { getViewDescription, PreloaderHandler, registry } from 'elsa-web-core';
import { reactElementWrapperFactory, ReactElementWrapper } from './ui-common';

// eslint-disable-next-line import/prefer-default-export
export const uiFactory = {
  createElement: async <T extends ReactElementWrapper>(id:string, ph:PreloaderHandler|null = null,
    operationId:string|null = null) => {
    const description = (await getViewDescription(id, ph, operationId));
    const factory = registry.get(reactElementWrapperFactory, description.view.name)!!;
    return (await factory.createWrapper(description)) as T;
  },
};
