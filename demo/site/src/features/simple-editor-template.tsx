// eslint-disable-next-line max-len
// eslint-disable-next-line no-unused-vars,import/prefer-default-export,no-use-before-define,max-classes-per-file
import React, { ReactNode } from 'react';
import { ViewDescription } from 'elsa-web-core';
import { Table } from 'antd';
import {
  BaseReactElementWrapperFactory, getNextCallbackId,
  reactComponentsCallbacks, ReactElementWrapper, ReactProxyView, viewInterceptorsRegistry,
} from './ui-common';

// eslint-disable-next-line no-unused-vars
export class SimpleEditorTemplate<VM, VC, VV> implements ReactElementWrapper {
  createReactElement(): React.ReactNode {
    return undefined;
  }
}

export class SimpleEditorTemplateFactory extends BaseReactElementWrapperFactory {
  // eslint-disable-next-line no-unused-vars
  async createWrapper(description: ViewDescription): Promise<ReactElementWrapper> {
    const wrapper = new SimpleEditorTemplate();
    return wrapper;
  }

  getId(): string {
    return 'simple-editor';
  }
}
