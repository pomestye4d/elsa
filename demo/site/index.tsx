// eslint-disable-next-line no-use-before-define
import React from 'react';
import ReactDOM from 'react-dom/client';
import { StepBackwardOutlined } from '@ant-design/icons';
import { Button, notification } from 'antd';
import 'antd/dist/antd.css';

import { demoL10nMessageBundle } from './src/gen/demo-test-l10n';

async function start() {
  await demoL10nMessageBundle.initialize();
  const root = ReactDOM.createRoot(
        document.getElementById('root') as HTMLElement,
  );
  root.render(
    <>
      <div>Hello world</div>
      <StepBackwardOutlined />
      <Button onClick={() => {
        notification.info({ message: demoL10nMessageBundle.Are_you_sure_to_delete({ id: 1, className: 'test', caption: 'Entity' }) });
      }}
      >
        Show alert
      </Button>
    </>,
  );
}

start();
