import { registry } from 'elsa-web-core';
import { ListTemplateFactory } from './list-template';
import './main';
import { TableTemplateFactory } from './table-template';
import { SimpleEditorTemplateFactory } from './simple-editor-template';
import { TextBoxWidgetFactory } from './text-box-widget-factory';

registry.register(new ListTemplateFactory());
registry.register(new TableTemplateFactory());
registry.register(new SimpleEditorTemplateFactory());
registry.register(new TextBoxWidgetFactory());
