package com.zzy.elf_template.template.engine;

import android.content.Context;

import com.zzy.core.ElfConstact;

/**
 * @author zzy
 * @date 2018/3/21
 */

public abstract class Engine implements ElfConstact.TemplateRender {
    public abstract Engine newInstance(Context context, int templateId, int layoutId, Object... args);
}
