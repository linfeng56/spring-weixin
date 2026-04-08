import type { App } from 'vue';
import PullToRefresh from './PullToRefresh';

PullToRefresh.install = (app: App) => {
  app.component(PullToRefresh.name, PullToRefresh);
};

export default PullToRefresh;
export { PullToRefresh };
