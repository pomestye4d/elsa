// eslint-disable-next-line max-classes-per-file
import { registry, ViewReference } from 'elsa-web-core';
import { BaseToolHandler, ViewInterceptor, viewInterceptorsRegistry } from './ui-common';
import { Constants, UsersList, UsersTableRowVM } from '../gen/demo-ui';
import { demoL10nMessageBundle } from '../gen/demo-test-l10n';
import {
  siteDeleteUser,
  siteGetUsers,
  siteSubscribeUsersModification, siteUpdateUser,
} from '../gen/demo-test-remoting';
import './elsa-demo.css';
import { uiFactory } from './ui-factory';

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

  async onClick() {
    await uiFactory.showDialog(
      demoL10nMessageBundle.New_User,
      Constants.UserEditor,
      { userName: 'New user' },
      null,
      true,
      async (dialog) => {
        const res = await siteUpdateUser({
          userId: -1,
          data: dialog.getView().getData(),
        });
        if (!res.success) {
          dialog.getView().showValidation(res.validation);
          return;
        }
        dialog.close();
        uiFactory.showNotification(demoL10nMessageBundle.User_created);
      },
    );
  }
}
registry.register(new CreateUserToolHandler());

class UpdateUserToolHandler extends BaseToolHandler<UsersList> {
  async initialize() {
    await demoL10nMessageBundle.initialize();
  }

  getPriority(): number {
    return 2;
  }

  getTitle(): string {
    return demoL10nMessageBundle.Edit;
  }

  async onClick(list: UsersList) {
    if (list.content.getSelectedRows().length === 0) {
      return;
    }
    const user = list.content.getSelectedRows()[0];
    await uiFactory.showDialog(
      demoL10nMessageBundle.Edit,
      Constants.UserEditor,
      { userName: user.userName },
      null,
      true,
      async (dialog) => {
        const res = await siteUpdateUser({
          userId: user.userId,
          data: dialog.getView().getData(),
        });
        if (!res.success) {
          dialog.getView().showValidation(res.validation);
          return;
        }
        dialog.close();
        uiFactory.showNotification(demoL10nMessageBundle.User_updated);
      },
    );
  }
}

registry.register(new UpdateUserToolHandler());

class DeleteUserToolHandler extends BaseToolHandler<UsersList> {
  async initialize() {
    await demoL10nMessageBundle.initialize();
  }

  getPriority(): number {
    return 1;
  }

  getTitle(): string {
    return demoL10nMessageBundle.Delete;
  }

  async onClick(view: UsersList) {
    const rows = view.content.getSelectedRows() as UsersTableRowVM[];
    if (rows.length === 0) {
      return;
    }
    uiFactory.confirm(demoL10nMessageBundle.Are_you_sure_to_delete({ id: rows[0].userId!, className: '', caption: rows[0].userName }), async () => {
      await siteDeleteUser({ userId: rows[0].userId! });
      uiFactory.showNotification(demoL10nMessageBundle.User_deleted);
    });
  }
}
registry.register(new DeleteUserToolHandler());

class ListTemplateInterceptor implements ViewInterceptor<UsersList> {
  async afterInitialize(view: UsersList) {
    const response = await siteGetUsers({});
    view.content.setData({
      items: response.users,
    });
    await siteSubscribeUsersModification({}, () => {
      setTimeout(async () => {
        const resp2 = await siteGetUsers({});
        view.content.setData({
          items: resp2.users,
        });
        view.content.clearRowSelection();
      }, 0);
      return true;
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
