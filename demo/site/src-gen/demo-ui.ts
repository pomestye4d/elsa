/* ****************************************************************
 * This is generated code, don't modify it manually
 **************************************************************** */

/* eslint-disable max-classes-per-file,no-unused-vars,max-len,lines-between-class-members,no-use-before-define  */
import {
  TextBoxWidgetConfiguration,
} from './demo-ui-template';
import {
  TableTemplate,
} from '../src/table-template';
import {
  ListTemplate,
} from '../src/list-template';

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
  userName?: string,
};

export type UsersTableRowVC={
  userName?: TextBoxWidgetConfiguration,
};

export type UsersTableRowVV={
  userName?: string,
};

export class UsersTable extends TableTemplate<UsersTableVM, UsersTableVC, UsersTableVV> {
}

export class UsersList extends ListTemplate<UsersListVM, UsersListVC, UsersListVV> {
  // @ts-ignore
  content: UsersTable;
}
