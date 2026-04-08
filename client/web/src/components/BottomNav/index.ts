import type { App } from 'vue';
import BottomNav from './BottomNav';

BottomNav.install = (app: App) => {
  app.component(BottomNav.name, BottomNav);
};

export default BottomNav;
export { BottomNav };
export type { NavItem } from './BottomNav';
