/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codenarc.rule.unnecessary

import org.codenarc.rule.AbstractRuleTestCase
import org.codenarc.rule.Rule

/**
 * Tests for UnnecessaryGStringRule
 *
 * @author 'Hamlet D'Arcy'
 * @version $Revision: 329 $ - $Date: 2010-04-29 04:20:25 +0200 (Thu, 29 Apr 2010) $
 */
class UnnecessaryGStringRuleTest extends AbstractRuleTestCase {

    void testRuleProperties() {
        assert rule.priority == 2
        assert rule.name == 'UnnecessaryGString'
    }

    void testSimpleCase() {
        final SOURCE = '''
            def docFile = "src/site/apt/codenarc-rules-${ruleSetName}.apt"
        '''
        assertNoViolations(SOURCE)
    }
    
    void testSuccessScenario() {
        final SOURCE = '''
        // OK
        def g = """
        I am a \\$ string
        """
        '''
        assertNoViolations(SOURCE)
    }

    void testSuccessScenario0() {
        final SOURCE = '''
        def h = """
        I am a $string
    """
        '''
        assertNoViolations(SOURCE)
    }

    void testSuccessScenario1() {
        final SOURCE = '''
        def i = 'i am a string'
        '''
        assertNoViolations(SOURCE)
    }

    void testSuccessScenario2() {
        final SOURCE = """
        def j = '''i am a
        string
        '''
        """
        assertNoViolations(SOURCE)
    }

    void testSuccessScenario3() {
        final SOURCE = '''
        def c = "I am a ' string"       // OK

        def d = """I am a ' string"""   // OK

        def e = """I am a ' string"""   // OK

        def f = "I am a \\$ string"  // OK

        '''
        assertNoViolations(SOURCE)
    }

    void testDoubleQuotes() {
        final SOURCE = '''
            def a = "I am a string"     // violation
        '''
        assertSingleViolation(SOURCE, 2, '"I am a string"', 'The String \'I am a string\' can be wrapped in single quotes instead of double quotes')
    }

    void testMultiline() {
        final SOURCE = '''
            def a = """
I am a string
"""     // violation
        '''
        assertSingleViolation(SOURCE, 2, 'def a = """', """The String '
I am a string
' can be wrapped in single quotes instead of double quotes""")
    }

    protected Rule createRule() {
        new UnnecessaryGStringRule()
    }
}