/*****************************************************************
 * This is generated code, don't modify it manually
 *****************************************************************/

package com.vga.platform.elsa.common.core.test.model.domain;

import com.vga.platform.elsa.common.core.search.ArgumentType;
import com.vga.platform.elsa.common.core.search.ComparisonSupport;
import com.vga.platform.elsa.common.core.search.EqualitySupport;
import com.vga.platform.elsa.common.core.search.FieldNameSupport;
import com.vga.platform.elsa.common.core.search.SortSupport;
import com.vga.platform.elsa.common.core.search.StringOperationsSupport;
import java.time.LocalDateTime;

public class TestDomainAssetFields{

	public final static _stringPropertyField stringProperty = new _stringPropertyField();

	public final static _datePropertyField dateProperty = new _datePropertyField();

	private static class _stringPropertyField extends FieldNameSupport implements EqualitySupport, StringOperationsSupport, SortSupport, ArgumentType<String>{
		_stringPropertyField(){
			super("stringProperty");
		}
	}

	private static class _datePropertyField extends FieldNameSupport implements ComparisonSupport, SortSupport, ArgumentType<LocalDateTime>{
		_datePropertyField(){
			super("dateProperty");
		}
	}
}