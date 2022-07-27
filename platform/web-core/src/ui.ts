import { performServerCall, PreloaderHandler, remotingConfiguration } from './remoting-low-level';

export type XmlNode = {
    name: string,
    value?: string,
    children: XmlNode[],
    attributes: Map<string, string>,
}

export type ViewDescription = {
    localizations: Map<string, string>,
    view: XmlNode,
}

const viewDescriptions = new Map<string, ViewDescription>();

// eslint-disable-next-line import/prefer-default-export
export const getViewDescription = async (
  viewId:string,
  ph:PreloaderHandler|null,
  operationId:string|null,
) => {
  if (!viewDescriptions.has(viewId)) {
    const description = (await performServerCall(remotingConfiguration.clientId, 'core', 'meta', 'get-view-description', {
      viewId,
    }, ph, operationId)) as ViewDescription;
    viewDescriptions.set(viewId, description);
  }
  return viewDescriptions.get(viewId) as ViewDescription;
};
