import { registry } from 'elsa-web-core';
import { ListTemplateFactory } from './list-template';
import './main';
import { TableTemplateFactory } from './table-template';
import { SimpleEditorTemplateFactory } from './simple-editor-template';

registry.register(new ListTemplateFactory());
registry.register(new TableTemplateFactory());
registry.register(new SimpleEditorTemplateFactory());
