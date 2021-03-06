/**
 * Danta AEM Bundle
 *
 * Copyright (C) 2017 Tikal Technologies, Inc. All rights reserved.
 *
 * Licensed under GNU Affero General Public License, Version v3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/agpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied;
 * without even the implied warranty of MERCHANTABILITY.
 * See the License for more details.
 */

package danta.aem.templating;

import danta.api.ContextProcessorEngine;
import danta.api.DOMProcessorEngine;
import danta.api.configuration.ConfigurationProvider;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.scripting.api.AbstractScriptEngineFactory;

import javax.script.ScriptEngine;
import java.util.ArrayList;
import java.util.List;


/**
 * Apache Sling Scripting Danta
 *
 * @author      jbarrera
 * @version     1.0.0
 * @since       2016-07-27
 */
@Component(
        label = "Apache Sling Scripting Danta",
        description = "Scripting engine for Danta",
        immediate = true,
        metatype = true
)
@Service
@Properties({
        @Property(name = "service.description", value = "Scripting engine for Danta"),
        @Property(name = "service.ranking", intValue = 0, propertyPrivate = false)
})
public class DantaScriptEngineFactory
        extends AbstractScriptEngineFactory {

    protected final static String ENGINE_NAME = "Danta Script Engine";
    protected final static String SCRIPT_NAME = "danta";
    protected final static String LANGUAGE_VERSION = "1.0";
    protected final static String LX_EXT = "d";

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY, policy = ReferencePolicy.STATIC)
    private ContextProcessorEngine contextProcessorEngine;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY, policy = ReferencePolicy.STATIC)
    private ConfigurationProvider configurationProvider;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY, policy = ReferencePolicy.STATIC)
    private ResourceResolverFactory resourceResolverFactory;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY, policy = ReferencePolicy.STATIC)
    private DOMProcessorEngine domProcessorEngine;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY, policy = ReferencePolicy.STATIC)
    private HelperFunctionBind helperFunctionBind;

    public DantaScriptEngineFactory() {
        setExtensions(LX_EXT);
        setNames(SCRIPT_NAME);
    }

    @Override
    public String getLanguageName() {
        return SCRIPT_NAME;
    }

    @Override
    public String getLanguageVersion() {
        return LANGUAGE_VERSION;
    }

    @Override
    public List<String> getExtensions() {
        ArrayList<String> extList = new ArrayList<String>();
        extList.add(LX_EXT);
        return extList;
    }

    @Override
    public String getEngineName() {
        return ENGINE_NAME;
    }

    @Override
    public ScriptEngine getScriptEngine() {
        return new DantaScriptEngine(this,
                contextProcessorEngine,
                configurationProvider,
                resourceResolverFactory,
                domProcessorEngine,
                helperFunctionBind
        );
    }

}
