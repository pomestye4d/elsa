/* ****************************************************************
 * This is generated code, don't modify it manually
 **************************************************************** */

/* eslint-disable camelcase */

import { BaseL10nBundle, EntityReference } from 'elsa-web-core';

class DemoL10nMessageBundle extends BaseL10nBundle {
  Yes = '???';

  Confirm = '???';

  User_deleted = '???';

  User_created = '???';

  User_updated = '???';

  No = '???';

  Create = '???';

  Edit = '???';

  Delete = '???';

  You_should_select_item = '???';

  New_User = '???';

  constructor() {
    super('demo-site');
  }

  Are_you_sure_to_delete(object: EntityReference) {
    return this.getMessage('Are_you_sure_to_delete', object);
  }
}

// eslint-disable-next-line import/prefer-default-export
export const demoL10nMessageBundle = new DemoL10nMessageBundle();
