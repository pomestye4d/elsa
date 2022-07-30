/* ****************************************************************
 * This is generated code, don't modify it manually
 **************************************************************** */

import {
  serverCall, PreloaderHandler, subscribe, unsubscribe,
} from 'elsa-web-core';

import {
  UserEditorVM,
  UserEditorVV,
} from './demo-ui';

export type DemoTestEnum=
'ITEM1'
| 'ITEM2';

export type DemoTestEntity={
  stringProperty?: string,
};

export type DemoSiteUser={
  userName: string,
  userId?: number,
};

export type DemoSiteGetUsersRequest={
};

export type DemoSiteGetUsersResponse={
  users: DemoSiteUser[],
};

export type DemoSiteUpdateUserRequest={
  userId: number,
  data: UserEditorVM,
};

export type DemoSiteUpdateUserResponse={
  success: boolean,
  validation?: UserEditorVV,
};

export type DemoSiteDeleteUserRequest={
  userId: number,
};

export type DemoSiteDeleteUserResponse={
};

export type UserModificationSubscriptionParameters={
};

export type UserModificationSubscriptionEvent={
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
export const siteGetUsers = async (request: DemoSiteGetUsersRequest, preloaderHandler: PreloaderHandler| boolean = true, operationId: string|null = null) => {
  // noinspection UnnecessaryLocalVariableJS
  const result = await serverCall<DemoSiteGetUsersRequest, DemoSiteGetUsersResponse>('demo', 'site', 'get-users', request, preloaderHandler, operationId);
  return result;
};

// eslint-disable-next-line max-len
export const siteUpdateUser = async (request: DemoSiteUpdateUserRequest, preloaderHandler: PreloaderHandler| boolean = true, operationId: string|null = null) => {
  // noinspection UnnecessaryLocalVariableJS
  const result = await serverCall<DemoSiteUpdateUserRequest, DemoSiteUpdateUserResponse>('demo', 'site', 'update-user', request, preloaderHandler, operationId);
  return result;
};

// eslint-disable-next-line max-len
export const siteDeleteUser = async (request: DemoSiteDeleteUserRequest, preloaderHandler: PreloaderHandler| boolean = true, operationId: string|null = null) => {
  // noinspection UnnecessaryLocalVariableJS
  const result = await serverCall<DemoSiteDeleteUserRequest, DemoSiteDeleteUserResponse>('demo', 'site', 'delete-user', request, preloaderHandler, operationId);
  return result;
};

// eslint-disable-next-line max-len,no-unused-vars
export const siteSubscribeUsersModification = async (parameters: UserModificationSubscriptionParameters, handler: (ev:UserModificationSubscriptionEvent) => boolean, preloaderHandler: PreloaderHandler| boolean = true, operationId: string|null = null) => {
  const subId = await subscribe<UserModificationSubscriptionParameters, UserModificationSubscriptionEvent>('demo', 'site', 'subscribe-users-modification', parameters, handler, preloaderHandler, operationId);
  return subId;
};

// eslint-disable-next-line max-len,no-unused-vars
export const unsubscribeSiteSubscribeUsersModification = async (subId: string, preloaderHandler: PreloaderHandler| boolean = true, operationId: string|null = null) => {
  await unsubscribe('demo', subId, preloaderHandler, operationId);
};

// eslint-disable-next-line max-len
export const testServerCall = async (request: DemoTestServerCallRequest, preloaderHandler: PreloaderHandler| boolean = true, operationId: string|null = null) => {
  // noinspection UnnecessaryLocalVariableJS
  const result = await serverCall<DemoTestServerCallRequest, DemoTestServerCallResponse>('demo', 'test', 'server-call', request, preloaderHandler, operationId);
  return result;
};
