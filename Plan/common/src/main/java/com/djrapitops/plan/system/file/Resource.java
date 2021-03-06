/*
 *  This file is part of Player Analytics (Plan).
 *
 *  Plan is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License v3 as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Plan is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Plan. If not, see <https://www.gnu.org/licenses/>.
 */
package com.djrapitops.plan.system.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Interface for accessing plugin resources in jar or plugin files.
 *
 * @author Rsl1122
 */
public interface Resource {

    /**
     * Get the name of this Resource.
     *
     * @return Relative file path given to {@link PlanFiles}.
     */
    String getResourceName();

    /**
     * Get the resource as an InputStream.
     *
     * @return InputStream of the resource, not closed automatically.
     * @throws IOException If the resource is unavailable.
     */
    InputStream asInputStream() throws IOException;

    /**
     * Get the resource as lines.
     *
     * @return Lines of the resource file.
     * @throws IOException If the resource is unavailable.
     */
    List<String> asLines() throws IOException;

    /**
     * Get the resource as a String with each line separated by CRLF newline characters {@code \r\n}.
     *
     * @return Flat string with each line separated by {@code \r\n}.
     * @throws IOException If the resource is unavailable.
     */
    String asString() throws IOException;

}