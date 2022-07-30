/* eslint-disable no-unused-vars */
// noinspection JSUnusedLocalSymbols,JSUnusedGlobalSymbols
// noinspection JSUnusedLocalSymbols

import {
  confirmSubscriptionEvent,
  performServerCall, performSubscription,
  performUnsubscription, PreloaderHandler, remotingConfiguration,
} from './remoting-low-level';
import { restoreObject, serializeToJson } from './serialization';

// Types
type ServerCallDescription = {
  requestClassName?: string,
  responseClassName?: string
}

type SubscriptionDescription = {
  parameterClassName: string,
  eventClassName: string
}

type ClientServerCallDescription = {
  requestClassName?: string,
  responseClassName?: string
}

type ClientResponse = {
  errorMessage?: string,
  requestId: string,
  responseStr?: string
}

const serverCallDescriptions = new Map<string, ServerCallDescription>();

const subscriptionDescriptions = new Map<string, SubscriptionDescription>();

const fakePreloaderHandler : PreloaderHandler = {
  showPreloader: () => null,
  hidePreloader: () => null,
  delay: 0,
};

// eslint-disable-next-line import/prefer-default-export
export async function serverCall<RQ, RP>(
  remotingId: string,
  groupId: string,
  methodId: string,
  request: RQ,
  preloaderHandler: PreloaderHandler| boolean,
  operationId:string|null,
) {
  const fullId = `${remotingId}:${groupId}:${methodId}`;
  // eslint-disable-next-line no-nested-ternary,max-len
  const ph = preloaderHandler === false ? null : (preloaderHandler === true
    ? (remotingConfiguration.globalPreloaderHandler || null) : preloaderHandler);
  if (!serverCallDescriptions.has(fullId)) {
    const description = await performServerCall(remotingConfiguration.clientId, 'core', 'meta', 'get-server-call-description', {
      remotingId,
      groupId,
      methodId,
    }, ph, operationId) as ServerCallDescription;
    serverCallDescriptions.set(fullId, description);
  }
  const rd = serverCallDescriptions.get(fullId)!!;
  let requestBody = null;
  if (rd.requestClassName) {
    const rq = JSON.parse(JSON.stringify(request));
    requestBody = await serializeToJson(rq, rd.requestClassName, ph, operationId);
  }
  // eslint-disable-next-line max-len
  const result = await performServerCall(remotingConfiguration.clientId, remotingId, groupId, methodId, requestBody, ph, operationId);
  if (result != null && rd.responseClassName) {
    await restoreObject(rd.responseClassName, result, ph, operationId);
  }
  return result as RP;
}

export async function subscribe<SP, SE>(
  remotingId: string,
  groupId: string,
  subscriptionId: string,
  parameter: SP,
  // eslint-disable-next-line no-unused-vars
  handler: (ev:SE) => boolean,
  preloaderHandler: PreloaderHandler| boolean,
  operationId:string|null,
) {
  const fullId = `${remotingId}:${groupId}:${subscriptionId}`;
  // eslint-disable-next-line no-nested-ternary,max-len
  const ph = preloaderHandler === false ? null : (preloaderHandler === true
    ? (remotingConfiguration.globalPreloaderHandler || null) : preloaderHandler);
  if (!subscriptionDescriptions.has(fullId)) {
    const description = (await performServerCall(remotingConfiguration.clientId, 'core', 'meta', 'get-subscription-description', {
      remotingId,
      groupId,
      subscriptionId,
    }, ph, operationId)) as SubscriptionDescription;
    subscriptionDescriptions.set(fullId, description);
  }
  const sd = subscriptionDescriptions.get(fullId)!!;
  const requestBody = await serializeToJson(parameter, sd.parameterClassName, ph, operationId);
  // eslint-disable-next-line max-len
  const subId = await performSubscription(
    remotingId,
    groupId,
    subscriptionId,
    requestBody,
    ph,
    operationId,
    async (data) => {
      const obj = JSON.parse(data);
      await restoreObject(sd.eventClassName, obj, ph, operationId);
      const result = handler(obj);
      // eslint-disable-next-line max-len
      await confirmSubscriptionEvent(remotingConfiguration.clientId, remotingId, subId, result);
    },
  );
  return subId as string;
}

export async function unsubscribe(
  remotingId: string,
  subscriptionId: string,
  preloaderHandler: PreloaderHandler| boolean,
  operationId:string|null,
) {
  // eslint-disable-next-line no-nested-ternary,max-len
  const ph = preloaderHandler === false ? null : (preloaderHandler === true
    ? (remotingConfiguration.globalPreloaderHandler || null) : preloaderHandler);

  return performUnsubscription(remotingId, subscriptionId, ph, operationId);
}
