// eslint-disable-next-line max-len
// eslint-disable-next-line no-unused-vars,import/prefer-default-export,no-use-before-define,max-classes-per-file
import React, { ReactElement } from 'react';
import { Editor, PreloaderHandler, ViewDescription } from 'elsa-web-core';
import { BaseViewFactory, ReactElementWrapper, viewInterceptorsRegistry } from './ui-common';
import { uiFactory } from './ui-factory';
import { Widget } from '../../../../platform/web-core/src/ui';

type SimpleEditorFieldContainer = {
  caption: string,
  id: string,
  element: ReactElementWrapper,
}
// eslint-disable-next-line no-unused-vars
export class SimpleEditorTemplate<VM, VC, VV> implements ReactElementWrapper, Editor<VM, VC, VV> {
  private elements: SimpleEditorFieldContainer[] = [];

  // eslint-disable-next-line max-len
  async initialize(description: ViewDescription, ph: PreloaderHandler | null, operationId: string | null) {
    for (let i = 0; i < description.view.children.length; i += 1) {
      const child = description.view.children[i];
      const id = child.attributes.get('id')!;
      const caption = description.localizations.get(id)!;
      // eslint-disable-next-line no-await-in-loop
      const widget = await uiFactory.createWidget(child.children[0], ph, operationId);
      this.elements.push({
        caption,
        element: widget,
        id,
      });
      (this as any)[id] = widget;
    }
    viewInterceptorsRegistry.getForViewId(description.view.attributes.get('id')!).forEach((it) => {
      it.afterInitialize(this);
    });
  }

  createReactElement(): ReactElement {
    return (
      <>
        {this.elements.map((data) => (
          <div className="simple-editor-field-wrapper" key={`wrapper-${data.id}`}>
            <div className="simple-editor-label" key={`label-${data.id}`}>
              {data.caption}
            </div>
            <div className="simple-editor-widget" key={`widget-${data.id}`}>
              {data.element.createReactElement()}
            </div>
          </div>
        ))}
      </>
    );
  }

  setData(vm: VM, vc?: VC|null) {
    this.elements.forEach((elm) => {
      // eslint-disable-next-line max-len
      (elm.element as unknown as Widget<any, any, any>).setData((vm as any)[elm.id]);
      const config = vc && (vc as any)[elm.id];
      if (config) {
        (elm.element as unknown as Widget<any, any, any>).configure(config);
      }
    });
  }

  getData() {
    const result = {} as VM;
    this.elements.forEach((elm) => {
      // eslint-disable-next-line max-len
      (result as any)[elm.id] = (elm.element as unknown as Widget<any, any, any>).getData();
    });
    return result;
  }

  showValidation(vv: VV) {
    this.elements.forEach((elm) => {
      // eslint-disable-next-line max-len
      (elm.element as unknown as Widget<any, any, any>).showValidation((vv as any)[elm.id]);
    });
  }
}

export class SimpleEditorTemplateFactory extends BaseViewFactory {
  // eslint-disable-next-line no-unused-vars,max-len
  async createWrapper(description: ViewDescription, ph:PreloaderHandler|null, operationId:string|null): Promise<ReactElementWrapper> {
    const wrapper = new SimpleEditorTemplate();
    await wrapper.initialize(description, ph, operationId);
    return wrapper;
  }

  getId(): string {
    return 'simple-editor';
  }
}
