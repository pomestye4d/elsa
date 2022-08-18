// eslint-disable-next-line no-use-before-define,no-unused-vars
import React from 'react';
import ReactDOM from 'react-dom/client';
import 'antd/dist/antd.css';
import './index.css';
import './src/features/activator';

import { Layout } from 'antd';
import { Header } from 'antd/es/layout/layout';
import { demoL10nMessageBundle } from './src/gen/demo-test-l10n';
import { uiFactory } from './src/features/ui-factory';
import { Constants } from './src/gen/demo-ui';

async function start() {
  await demoL10nMessageBundle.initialize();
  const root = ReactDOM.createRoot(
        document.getElementById('root') as HTMLElement,
  );
  const main = await (uiFactory.createView(Constants.UsersList));
  root.render(
    <Layout id="main-layout">
      <Header id="main-layout-header">
        Elsa demo app
      </Header>
      {main.createReactElement()}
    </Layout>,
  );
}

start();
