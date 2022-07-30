/* ****************************************************************
 * This is generated code, don't modify it manually
 **************************************************************** */

/* eslint-disable max-classes-per-file,no-unused-vars,max-len,lines-between-class-members,no-use-before-define  */
import { ViewReference } from 'elsa-web-core';
import {
  TextBoxWidgetConfiguration,
} from './demo-ui-template';
import {
  TableTemplate,
} from '../features/table-template';
import {
  ListTemplate,
} from '../features/list-template';
import {
  SimpleEditorTemplate,
} from '../features/simple-editor-template';

export type UsersListVM={
  content?: UsersTableVM,
};

export type UsersListVC={
  content?: UsersTableVC,
};

export type UsersListVV={
  content?: UsersTableVV,
};

export type UsersTableVM={
  items: UsersTableRowVM[],
};

export type UsersTableVC={
  items: UsersTableRowVC[],
};

export type UsersTableVV={
  items: UsersTableRowVV[],
};

export type UsersTableRowVM={
  userId?: number,
  userName?: string,
};

export type UsersTableRowVC={
};

export type UsersTableRowVV={
};

export type UserEditorVM={
  userName?: string,
};

export type UserEditorVC={
  userName?: TextBoxWidgetConfiguration,
};

export type UserEditorVV={
  userName?: string,
};

export class UsersTable extends TableTemplate<UsersTableVM, UsersTableVC, UsersTableVV> {
}

export class UsersList extends ListTemplate<UsersListVM, UsersListVC, UsersListVV> {
  // @ts-ignore
  content: UsersTable;
}

export class UserEditor extends SimpleEditorTemplate<UserEditorVM, UserEditorVC, UserEditorVV> {
}

export const Constants = {
  UsersTable: new ViewReference<UsersTable>('com.vga.platform.elsa.demo.ui.UsersTable'),
  UsersList: new ViewReference<UsersList>('com.vga.platform.elsa.demo.ui.UsersList'),
  UserEditor: new ViewReference<UserEditor>('com.vga.platform.elsa.demo.ui.UserEditor'),
};
