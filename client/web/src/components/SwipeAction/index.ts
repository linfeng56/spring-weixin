import type { App } from 'vue';
import SwipeAction from './SwipeAction';

SwipeAction.install = (app: App) => {
  app.component(SwipeAction.name, SwipeAction);
};

export default SwipeAction;
export { SwipeAction };
export type { SwipeActionButton } from './SwipeAction';
