/*
 * MIT License
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 *****************************************************************
 * This is generated code, don't modify it manually
 *****************************************************************/

package com.vga.platform.elsa.common.core.test.model.domain;

import com.vga.platform.elsa.common.core.model.domain.EntityReference;
import com.vga.platform.elsa.common.core.search.ArgumentType;
import com.vga.platform.elsa.common.core.search.CollectionSupport;
import com.vga.platform.elsa.common.core.search.EqualitySupport;
import com.vga.platform.elsa.common.core.search.FieldNameSupport;
import com.vga.platform.elsa.common.core.search.SortSupport;
import com.vga.platform.elsa.common.core.search.StringOperationsSupport;
import com.vga.platform.elsa.common.core.test.model.domain.TestDomainDocument;
import com.vga.platform.elsa.common.core.test.model.domain.TestEnum;

public class TestDomainDocumentProjectionFields{

	public final static _stringPropertyField stringProperty = new _stringPropertyField();

	public final static _getAllPropertyField getAllProperty = new _getAllPropertyField();

	public final static _enumPropertyField enumProperty = new _enumPropertyField();

	public final static _entityReferenceField entityReference = new _entityReferenceField();

	public final static _stringCollectionField stringCollection = new _stringCollectionField();

	public final static _enumCollectionField enumCollection = new _enumCollectionField();

	public final static _entityRefCollectionField entityRefCollection = new _entityRefCollectionField();

	private static class _stringPropertyField extends FieldNameSupport implements EqualitySupport, StringOperationsSupport, SortSupport, ArgumentType<String>{
		_stringPropertyField(){
			super("stringProperty");
		}
	}

	private static class _getAllPropertyField extends FieldNameSupport implements EqualitySupport, StringOperationsSupport, SortSupport, ArgumentType<String>{
		_getAllPropertyField(){
			super("getAllProperty");
		}
	}

	private static class _enumPropertyField extends FieldNameSupport implements EqualitySupport, SortSupport, ArgumentType<TestEnum>{
		_enumPropertyField(){
			super("enumProperty");
		}
	}

	private static class _entityReferenceField extends FieldNameSupport implements SortSupport, EqualitySupport, ArgumentType<EntityReference<TestDomainDocument>>{
		_entityReferenceField(){
			super("entityReference");
		}
	}

	private static class _stringCollectionField extends FieldNameSupport implements CollectionSupport, ArgumentType<String>{
		_stringCollectionField(){
			super("stringCollection");
		}
	}

	private static class _enumCollectionField extends FieldNameSupport implements CollectionSupport, ArgumentType<TestEnum>{
		_enumCollectionField(){
			super("enumCollection");
		}
	}

	private static class _entityRefCollectionField extends FieldNameSupport implements CollectionSupport, ArgumentType<EntityReference<TestDomainDocument>>{
		_entityRefCollectionField(){
			super("entityRefCollection");
		}
	}
}