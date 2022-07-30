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
  function processAttributes(view: XmlNode) {
    const attrs = view.attributes as unknown as [];
    // eslint-disable-next-line no-param-reassign
    view.attributes = new Map<string, string>();
    attrs.forEach((attr: any) => view.attributes.set(attr.key, attr.value));
    view.children.forEach((child) => processAttributes(child));
  }

  if (!viewDescriptions.has(viewId)) {
    const description = (await performServerCall(remotingConfiguration.clientId, 'core', 'meta', 'get-view-description', {
      viewId,
    }, ph, operationId)) as ViewDescription;
    const locs = description.localizations as unknown as [];
    description.localizations = new Map<string, string>();
    locs.forEach((loc: any) => description.localizations.set(loc.key, loc.value));
    processAttributes(description.view);
    viewDescriptions.set(viewId, description);
  }
  return viewDescriptions.get(viewId) as ViewDescription;
};

// eslint-disable-next-line no-unused-vars
export class ViewReference<T> {
  id:string;

  constructor(id:string) {
    this.id = id;
  }
}
