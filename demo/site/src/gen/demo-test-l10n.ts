/* ****************************************************************
 * This is generated code, don't modify it manually
 **************************************************************** */

/* eslint-disable camelcase */

import { BaseL10nBundle, EntityReference } from 'elsa-web-core';

class DemoL10nMessageBundle extends BaseL10nBundle {
  Yes = '???';

  constructor() {
    super('demo-site');
  }

  Are_you_sure_to_delete(object: EntityReference) {
    return this.getMessage('Are_you_sure_to_delete', object);
  }
}

// eslint-disable-next-line import/prefer-default-export
export const demoL10nMessageBundle = new DemoL10nMessageBundle();