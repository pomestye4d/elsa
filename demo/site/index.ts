import { testServerCall } from './src-gen/demo-test-remoting';
import { demoL10nMessageBundle } from './src-gen/demo-test-l10n';

(window as any).performTest = async () => {
  const res = await testServerCall({ param: 'test' });
  console.log(res);
  await demoL10nMessageBundle.initialize();
  console.log(demoL10nMessageBundle.Yes);
};
