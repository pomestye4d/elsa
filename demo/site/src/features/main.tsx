// eslint-disable-next-line max-classes-per-file
import { registry, ViewReference } from 'elsa-web-core';
import { BaseToolHandler, ViewInterceptor, viewInterceptorsRegistry } from './ui-common';
import { Constants, UsersList } from '../gen/demo-ui';
import { demoL10nMessageBundle } from '../gen/demo-test-l10n';
import { siteGetUsers } from '../gen/demo-test-remoting';

class CreateUserToolHandler extends BaseToolHandler<UsersList> {
  async initialize() {
    await demoL10nMessageBundle.initialize();
  }

  getPriority(): number {
    return 0;
  }

  getTitle(): string {
    return demoL10nMessageBundle.Create;
  }

  async onClick(view: UsersList) {
    console.log(view);
  }
}
registry.register(new CreateUserToolHandler());

class ListTemplateInterceptor implements ViewInterceptor<UsersList> {
  async afterInitialize(view: UsersList) {
    const response = await siteGetUsers({});
    view.content.setData({
      items: response.users,
    });
  }

  getPriority(): number {
    return 0;
  }

  getViewRef(): ViewReference<UsersList> {
    return Constants.UsersList;
  }
}

viewInterceptorsRegistry.register(new ListTemplateInterceptor());
