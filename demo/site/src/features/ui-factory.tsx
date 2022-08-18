// eslint-disable-next-line no-use-before-define
import React from 'react';
import {
  Editor, getViewDescription, PreloaderHandler, registry, ViewReference, XmlNode,
} from 'elsa-web-core';
import { Modal, notification } from 'antd';
import ReactDOM from 'react-dom/client';
import {
  Dialog,
  getNextCallbackId,
  reactComponentsCallbacks, ReactDialogProxy,
  ReactElementWrapper,
  reactViewFactory,
  reactWidgetFactory,
} from './ui-common';
import { demoL10nMessageBundle } from '../gen/demo-test-l10n';

// eslint-disable-next-line import/prefer-default-export
export const uiFactory = {
  // eslint-disable-next-line max-len
  createViewForId: async <T extends ReactElementWrapper>(id: string, ph:PreloaderHandler|null = null,
    operationId:string|null = null) => {
    const description = (await getViewDescription(id, ph, operationId));
    const factory = registry.get(reactViewFactory, description.view.name)!!;
    return (await factory.createWrapper(description, ph, operationId)) as T;
  },
  // eslint-disable-next-line max-len
  createView: async <T extends ReactElementWrapper>(ref: ViewReference<T>, ph:PreloaderHandler|null = null,
    operationId:string|null = null) => uiFactory.createViewForId(ref.id, ph, operationId),
  // eslint-disable-next-line max-len
  createWidget: async <T extends ReactElementWrapper>(node: XmlNode, ph:PreloaderHandler|null = null,
    operationId:string|null = null) => {
    const factory = registry.get(reactWidgetFactory, node.name)!!;
    return (await factory.createWrapper(node, ph, operationId)) as T;
  },
  confirm: (question:string, action: () =>Promise<void>) => {
    Modal.confirm({
      title: demoL10nMessageBundle.Confirm,
      content: question,
      okText: demoL10nMessageBundle.Yes,
      cancelText: demoL10nMessageBundle.No,
      onOk: () => {
        action();
      },
    });
  },
  showNotification: (text:string) => {
    notification.info({
      message: text,
    });
  },
  // eslint-disable-next-line max-len
  showDialog: async <VM, VC, VV, T extends ReactElementWrapper&Editor<VM, VC, VV>>(title: string, ref: ViewReference<T>, vm:VM,
  // eslint-disable-next-line no-unused-vars,max-len
    vc: VC| null, showCancel:boolean, onOk: (dialog:Dialog<T>) => Promise<void>, ph:PreloaderHandler|null = null,
    operationId:string|null = null) => {
    const description = (await getViewDescription(ref.id, ph, operationId));
    const factory = registry.get(reactViewFactory, description.view.name)!!;
    const wrapper = await factory.createWrapper(description, ph, operationId);
    const id = getNextCallbackId();
    reactComponentsCallbacks.set(id, {
      dialogElementWrapper: wrapper,
      dialogCallback: async (aDialog:Dialog<any>) => {
        await onOk(aDialog);
      },
    });
    const elm = wrapper as unknown as Editor<VM, VC, VV>;
    elm.setData(vm, vc);
    let dialogDiv = document.getElementById('dialog');
    if (dialogDiv) {
      document.getElementsByTagName('body')[0].removeChild(dialogDiv);
    }
    dialogDiv = document.createElement('div');
    dialogDiv.setAttribute('id', 'dialog');
    document.getElementsByTagName('body')[0].appendChild(dialogDiv);
    const dialogRoot = ReactDOM.createRoot(
        dialogDiv as HTMLElement,
    );
    dialogRoot.render(
      <ReactDialogProxy callbackId={id} title={title} />,
    );
  },
};
