import type { App } from 'vue';
import MobileLayout from './MobileLayout';

MobileLayout.install = (app: App) => {
  app.component(MobileLayout.name, MobileLayout);
};

export default MobileLayout;
export { MobileLayout };
