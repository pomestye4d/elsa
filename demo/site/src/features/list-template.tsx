// eslint-disable-next-line max-len
// eslint-disable-next-line no-unused-vars,import/prefer-default-export,max-classes-per-file,no-use-before-define
import React, { ReactElement } from 'react';
import { ViewDescription } from 'elsa-web-core';
import { BaseReactElementWrapperFactory, ReactElementWrapper } from './ui-common';

// eslint-disable-next-line import/prefer-default-export,no-unused-vars
export class ListTemplate<VM, VC, VV> implements ReactElementWrapper {
  createReactElement(): ReactElement {
    return <div>Test</div>;
  }
}

export class ListTemplateFactory extends BaseReactElementWrapperFactory {
  // eslint-disable-next-line no-unused-vars
  createWrapper(description: ViewDescription): Promise<ReactElementWrapper> {
    return Promise.resolve(new ListTemplate());
  }

  getId(): string {
    return 'list-with-tools';
  }
}
