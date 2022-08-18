// eslint-disable-next-line max-len
// eslint-disable-next-line no-unused-vars,import/prefer-default-export,max-classes-per-file,no-use-before-define
import React, { ReactElement, ReactNode } from 'react';
import { registry, RegistryItem, ViewDescription } from 'elsa-web-core';
import { Button, Layout } from 'antd';
import { Content, Header } from 'antd/es/layout/layout';
import {
  BaseViewFactory,
  ReactElementWrapper,
  ToolHandler,
  toolHandlersRegistryItemType, viewInterceptorsRegistry,
} from './ui-common';
import { uiFactory } from './ui-factory';

// eslint-disable-next-line import/prefer-default-export,no-unused-vars
export class ListTemplate<VM, VC, VV> implements ReactElementWrapper {
  private handlers:ToolHandler<any>[] = [];

  // @ts-ignore
  private contentElement: ReactElementWrapper;

  async initialize(description: ViewDescription) {
    const lst = registry.allOf(toolHandlersRegistryItemType);
    lst.forEach((it) => { it.initialize(); this.handlers.push(it); });
    this.handlers.sort((a, b) => a.getPriority() - b.getPriority());
    const containerRef = description.view.children[0].attributes.get('container-ref')!;
    this.contentElement = await uiFactory.createViewForId(containerRef, null, null);
    (this as any)[description.view.children[0].name] = this.contentElement;
    viewInterceptorsRegistry.getForViewId(description.view.attributes.get('id')!).forEach((it) => {
      it.afterInitialize(this);
    });
  }

  createReactElement(): ReactElement {
    return (
      <Layout className="content-layout">
        <Header className="content-layout-header">
          {this.handlers.map((h) => (
            <Button
              key={`tool-handler-${(h as unknown as RegistryItem<any>).getId()}`}
              className="tool-handler"
              onClick={() => {
                h.onClick(this);
              }}
            >
              {h.getTitle()}
            </Button>
          ))}
          <div className="content-layout-header-glue" />
        </Header>
        <Content
          className="content-layout-content"
        >
          {this.contentElement.createReactElement()}
        </Content>
      </Layout>
    );
  }
}

export class ListTemplateFactory extends BaseViewFactory {
  // eslint-disable-next-line no-unused-vars
  async createWrapper(description: ViewDescription): Promise<ReactElementWrapper> {
    const wrapper = new ListTemplate();
    await wrapper.initialize(description);
    return wrapper;
  }

  getId(): string {
    return 'list-with-tools';
  }
}
