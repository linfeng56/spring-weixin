import { test, expect } from '@playwright/test';

test.describe('移动端H5支持 - 功能测试', () => {
  test('响应式布局 - 移动端屏幕', async ({ page }) => {
    await page.setViewportSize({ width: 375, height: 812 });
    await page.goto('/');
    
    const body = page.locator('body');
    await expect(body).not.toHaveCSS('overflow-x', 'scroll');
    
    const sidebar = page.locator('.ant-layout-sider');
    await expect(sidebar).toBeHidden();
  });

  test('响应式布局 - 平板屏幕', async ({ page }) => {
    await page.setViewportSize({ width: 768, height: 1024 });
    await page.goto('/');
    
    const sidebar = page.locator('.ant-layout-sider');
    await expect(sidebar).toBeVisible();
  });

  test('底部导航栏显示', async ({ page }) => {
    await page.setViewportSize({ width: 375, height: 812 });
    await page.goto('/');
    
    const bottomNav = page.locator('.bottom-nav');
    await expect(bottomNav).toBeVisible();
  });

  test('触摸手势 - 左滑删除按钮', async ({ page }) => {
    await page.setViewportSize({ width: 375, height: 812 });
    await page.goto('/plan/week');
    
    const firstCard = page.locator('.plan-card').first();
    if (await firstCard.isVisible()) {
      const box = await firstCard.boundingBox();
      if (box) {
        await page.mouse.move(box.x + box.width - 10, box.y + box.height / 2);
        await page.mouse.down();
        await page.mouse.move(box.x - 100, box.y + box.height / 2, { steps: 10 });
        await page.mouse.up();
        
        const deleteButton = page.locator('.swipe-action-button:has-text("删除")');
        await expect(deleteButton).toBeVisible();
      }
    }
  });

  test('下拉刷新功能', async ({ page }) => {
    await page.setViewportSize({ width: 375, height: 812 });
    await page.goto('/plan/week');
    
    const content = page.locator('.pull-to-refresh-content');
    if (await content.isVisible()) {
      const box = await content.boundingBox();
      if (box) {
        await page.mouse.move(box.x + box.width / 2, box.y + 50);
        await page.mouse.down();
        await page.mouse.move(box.x + box.width / 2, box.y - 100, { steps: 10 });
        await page.mouse.up();
        
        const indicator = page.locator('.pull-to-refresh-indicator');
        await expect(indicator).toBeVisible();
      }
    }
  });
});

test.describe('PWA功能测试', () => {
  test('Manifest文件加载', async ({ page }) => {
    const response = await page.goto('/manifest.json');
    expect(response?.status()).toBe(200);
    
    const manifest = await page.evaluate(() => {
      return fetch('/manifest.json').then(r => r.json());
    });
    
    expect(manifest.name).toBe('工作计划管理系统');
    expect(manifest.short_name).toBe('计划管理');
    expect(manifest.display).toBe('standalone');
  });

  test('Service Worker注册', async ({ page }) => {
    await page.goto('/');
    
    const swRegistered = await page.evaluate(() => {
      return navigator.serviceWorker.ready.then(() => true).catch(() => false);
    });
    
    expect(swRegistered).toBe(true);
  });
});

test.describe('性能测试', () => {
  test('首屏加载时间', async ({ page }) => {
    const startTime = Date.now();
    await page.goto('/');
    await page.waitForLoadState('networkidle');
    const loadTime = Date.now() - startTime;
    
    console.log(`首屏加载时间: ${loadTime}ms`);
    expect(loadTime).toBeLessThan(3000);
  });

  test('列表滚动流畅度', async ({ page }) => {
    await page.setViewportSize({ width: 375, height: 812 });
    await page.goto('/plan/week');
    
    const list = page.locator('.ant-table-row').first();
    
    const metrics = [];
    for (let i = 0; i < 10; i++) {
      await page.mouse.wheel(0, 100);
      await page.waitForTimeout(50);
      
      const frameTime = await page.evaluate(() => {
        return performance.now();
      });
      metrics.push(frameTime);
    }
    
    console.log('滚动性能测试完成');
  });
});
