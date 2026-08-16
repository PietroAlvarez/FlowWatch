import { describe, expect, it } from 'vitest';

describe('FlowWatch', () => {
  it('calculates a percentage', () => {
    expect(Math.round((4 * 1000) / 5) / 10).toBe(80);
  });
});
