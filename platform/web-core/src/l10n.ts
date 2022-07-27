// eslint-disable-next-line max-classes-per-file
import { serverCall } from './remoting';
import { PreloaderHandler } from './remoting-low-level';

type GetBundleResponse = {
  messages: Map<string, string>
}

type GetBundleRequest = {
  bundleId: string,
}

// eslint-disable-next-line import/prefer-default-export
export class BaseL10nBundle {
  private readonly bundleId: string;

  private initialized = false;

  private localizations = new Map<string, string>();

  constructor(bundleId: string) {
    this.bundleId = bundleId;
  }

  async initialize(
    preloaderHandler: PreloaderHandler| boolean = true,
    operationId:string|null = null,
  ) {
    if (this.initialized) {
      return;
    }
    const result = await serverCall<GetBundleRequest, GetBundleResponse>(
      'core',
      'l10n',
      'get-bundle',
      { bundleId: this.bundleId },
      preloaderHandler,
      operationId,
    );
    result.messages.forEach((value, key) => {
      this.localizations.set(key, value);
      if (typeof (this as any)[key] === 'string') {
        (this as any)[key] = value;
      }
    });
    this.initialized = true;
  }

  private toString(param:any) {
    if (typeof param === 'object' && typeof param.id === 'number') {
      return param.caption;
    }
    return param.toString();
  }

  getMessage(id:string, ...parameters: any|null[]) {
    const message = this.localizations.get(id) || id;
    if (parameters.length === 0) {
      return message;
    }
    let result = message;
    for (let n = 0; n < parameters.length; n += 1) {
      result = result.replace(`{${n}}`, this.toString(parameters[n]) || '???');
    }
    return result;
  }
}
