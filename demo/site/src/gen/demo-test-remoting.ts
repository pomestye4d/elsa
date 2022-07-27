/* ****************************************************************
 * This is generated code, don't modify it manually
 **************************************************************** */

import { serverCall, PreloaderHandler } from 'elsa-web-core';

export type DemoTestEnum=
'ITEM1'
| 'ITEM2';

export type DemoTestEntity={
  stringProperty?: string,
};

export type DemoTestServerCallRequest={
  param?: string,
};

export type DemoTestServerCallResponse={
  stringProperty?: string,
  dateProperty?: Date,
  dateTimeProperty?: Date,
  enumProperty?: DemoTestEnum,
  stringCollection: string[],
  dateCollection: Date[],
  entityCollection: DemoTestEntity[],
  stringMap: Map<string, string>,
  dateMap: Map<Date, Date>,
  entityMap: Map<DemoTestEntity, DemoTestEntity>,
};

// eslint-disable-next-line max-len
export const testServerCall = async (request: DemoTestServerCallRequest, preloaderHandler: PreloaderHandler| boolean = true, operationId: string|null = null) => {
  // noinspection UnnecessaryLocalVariableJS
  const result = await serverCall<DemoTestServerCallRequest, DemoTestServerCallResponse>('demo', 'test', 'server-call', request, preloaderHandler, operationId);
  return result;
};
