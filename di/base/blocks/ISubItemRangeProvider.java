package kappafox.di.base.blocks;

import java.util.HashMap;
import com.google.common.collect.Range;

public interface ISubItemRangeProvider 
{
	public HashMap<Short, Range> getRangeSet( );
}
