// eslint-disable-next-line max-len
// eslint-disable-next-line no-unused-vars,import/prefer-default-export,no-use-before-define,max-classes-per-file
import React, { ReactNode } from 'react';
import { XmlNode, Widget } from 'elsa-web-core';
import { Input, Tooltip } from 'antd';
import {
  BaseWidgetFactory,
  getNextCallbackId,
  reactComponentsCallbacks,
  ReactElementWrapper,
  ReactProxyView,
} from './ui-common';
import { TextBoxWidgetConfiguration } from '../gen/demo-ui-template';

// eslint-disable-next-line no-unused-vars,max-len
export class TextBoxWidget implements ReactElementWrapper, Widget<string, TextBoxWidgetConfiguration, string> {
    private tableRef = React.createRef<ReactProxyView>()

    private value: string| null = null;

    private validation: string|null = null;

    private readonly callbackId = getNextCallbackId();

    // eslint-disable-next-line no-unused-vars
    async initialize(description: XmlNode) {
      const callbacks = {
        render: () => {
          if (this.tableRef.current?.state && (this.tableRef.current?.state as any).validation) {
            return (
              <Tooltip title={(this.tableRef.current?.state as any).validation}>
                <Input
                  className="widget-with-error"
                  type="text"
                  onChange={(event) => {
                    // eslint-disable-next-line max-len
                    (reactComponentsCallbacks.get(this.callbackId) as any).setValue(event.target.value);
                  }}
                  value={(this.tableRef.current?.state as any).value || ''}
                />
              </Tooltip>
            );
          }
          return (
            <Input
              type="text"
              onChange={(event) => {
                (reactComponentsCallbacks.get(this.callbackId) as any).setValue(event.target.value);
              }}
                // @ts-ignore
              value={this.tableRef.current?.state?.value || ''}
            />
          );
        },
        setValue: (value: string| null) => {
          this.value = value;
          this.tableRef.current?.setState({
            value: this.value,
          });
        },
        onDidMount: (comp: any) => {
          comp.setState({
            value: this.value,
          });
        },
      };
      reactComponentsCallbacks.set(this.callbackId, callbacks);
    }

    setData(data:string) {
      (reactComponentsCallbacks.get(this.callbackId) as any).setValue(data);
    }

    createReactElement(): ReactNode {
      return React.createElement(ReactProxyView as any, {
        ref: this.tableRef,
        callbackId: this.callbackId,
      });
    }

    configure(): void {
      // noops
    }

    getData() {
      return this.value;
    }

    showValidation(vv: string | null): void {
      this.validation = vv;
      this.tableRef.current?.setState({
        validation: this.validation,
      });
    }
}

export class TextBoxWidgetFactory extends BaseWidgetFactory {
  // eslint-disable-next-line no-unused-vars
  async createWrapper(node: XmlNode): Promise<ReactElementWrapper> {
    const wrapper = new TextBoxWidget();
    await wrapper.initialize(node);
    return wrapper;
  }

  getId(): string {
    return 'text-box-widget';
  }
}
