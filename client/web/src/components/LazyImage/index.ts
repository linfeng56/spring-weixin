import type { App } from 'vue';
import LazyImage from './index';

LazyImage.install = (app: App) => {
  app.component(LazyImage.name, LazyImage);
};

export default LazyImage;
export { LazyImage };
