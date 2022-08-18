// eslint-disable-next-line max-len
// eslint-disable-next-line no-unused-vars,import/prefer-default-export,no-use-before-define,max-classes-per-file
import React, { ReactNode } from 'react';
import { ViewDescription } from 'elsa-web-core';
import { Table } from 'antd';
import {
  BaseViewFactory, getNextCallbackId,
  reactComponentsCallbacks, ReactElementWrapper, ReactProxyView, viewInterceptorsRegistry,
} from './ui-common';

// eslint-disable-next-line no-unused-vars
export class TableTemplate<VM, VC, VV> implements ReactElementWrapper {
    private tableRef = React.createRef<ReactProxyView>()

    private items: any[] = []

    private readonly callbackId = getNextCallbackId();

    private selectedRows: any[] = [];

    private selectedRowKeys:React.Key[] = [];

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

      const callbacks = {
        render: () => (
          <Table
            rowSelection={{
              type: 'radio',
              onChange: (selectedRowKeys: React.Key[], selectedRows: any[]) => {
                // eslint-disable-next-line max-len
                (reactComponentsCallbacks.get(this.callbackId) as any).setSelectedRows(selectedRowKeys, selectedRows);
              },
              // @ts-ignore
              selectedRowKeys: this.tableRef.current?.state?.selectedRowKeys || [],
            }}
            columns={columns}
            dataSource={this.items.map((item, n) => ({
              key: `row-number-${n}`,
              ...item,
            }))}
            pagination={false}
          />
        ),
        setSelectedRows: (keys:React.Key[], rows:any[]) => {
          this.selectedRows = rows;
          this.selectedRowKeys = keys;
          this.tableRef.current?.setState({
            selectedRowKeys: this.selectedRowKeys,
          });
        },
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

    clearRowSelection() {
      this.selectedRowKeys = [];
      this.tableRef.current?.setState({
        selectedRowKeys: this.selectedRowKeys,
      });
    }

    getSelectedRows() {
      return this.selectedRows;
    }

    createReactElement(): ReactNode {
      return React.createElement(ReactProxyView as any, {
        ref: this.tableRef,
        callbackId: this.callbackId,
      });
    }
}

export class TableTemplateFactory extends BaseViewFactory {
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
