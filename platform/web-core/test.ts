// eslint-disable-next-line import/prefer-default-export
import { serverCall } from './src/remoting';

const performTest = async () => {
  const result = await serverCall<any, any>(
    'demo',
    'test',
    'server-call',
    { param: 'test' },
    false,
    null,
  );
  console.log(result);
};

(window as any).performTest = () => {
  performTest();
};
