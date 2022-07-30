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
export class TableTemplate<VM, VC, VV> implements ReactElementWrapper {
    private tableRef = React.createRef<ReactProxyView>()

    private items: any[] = []

    private readonly callbackId = getNextCallbackId();

    // eslint-disable-next-line no-unused-vars
    async initialize(description: ViewDescription) {
      const columns = description.view.children.filter((columnChild) => columnChild.attributes.get('hidden') !== 'true').map((columnChild) => {
        const dataIndex = columnChild.attributes.get('id')!;
        const title = description.localizations.get(dataIndex) ?? dataIndex;
        return {
          title,
          dataIndex,
        };
      });

      // rowSelection object indicates the need for row selection
      const rowSelection = {
        onChange: (selectedRowKeys: React.Key[], selectedRows: any[]) => {
          console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
        },
      };
      const callbacks = {
        render: () => (
          <Table
            rowSelection={{
              type: 'radio',
              ...rowSelection,
            }}
            columns={columns}
            dataSource={this.items.map((item, n) => ({
              key: `row-number-${n}`,
              ...item,
            }))}
            pagination={false}
          />
        ),
      };
      reactComponentsCallbacks.set(this.callbackId, callbacks);
      viewInterceptorsRegistry.getForViewId(description.view.attributes.get('id')!).forEach((it) => {
        it.afterInitialize(this);
      });
    }

    setData(data:VM) {
      this.items = (data as any).items;
      if (this.tableRef.current?.isMounted()) {
        this.tableRef.current?.forceUpdate();
      }
    }

    createReactElement(): ReactNode {
      return React.createElement(ReactProxyView as any, {
        ref: this.tableRef,
        callbackId: this.callbackId,
      });
    }
}

export class TableTemplateFactory extends BaseReactElementWrapperFactory {
  // eslint-disable-next-line no-unused-vars
  async createWrapper(description: ViewDescription): Promise<ReactElementWrapper> {
    const wrapper = new TableTemplate();
    await wrapper.initialize(description);
    return wrapper;
  }

  getId(): string {
    return 'table';
  }
}
