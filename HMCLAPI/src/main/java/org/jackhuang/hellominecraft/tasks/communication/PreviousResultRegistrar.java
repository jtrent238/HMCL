/*
 * Copyright 2013 huangyuhui <huanghongxun2008@126.com>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.
 */
package org.jackhuang.hellominecraft.tasks.communication;

import org.jackhuang.hellominecraft.tasks.Task;

/**
 *
 * @author huangyuhui
 * @param <T> Previous task result type
 */
public interface PreviousResultRegistrar<T> {
    
    /**
     * 
     * @param pr previous task handler
     * @return task self instance
     */
    Task registerPreviousResult(PreviousResult<T> pr);
}
