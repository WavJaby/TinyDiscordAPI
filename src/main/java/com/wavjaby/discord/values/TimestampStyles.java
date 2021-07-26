package com.wavjaby.discord.values;

public class TimestampStyles {
    //t	    16:20	                        Short Time
    //T	    16:20:30	                    Long Time
    //d	    20/04/2021	                    Short Date
    //D	    20 April 2021	                Long Date
    //f     20 April 2021 16:20	            Short Date/Time
    //F	    Tuesday, 20 April 2021 16:20	Long Date/Time
    //R	    2 months ago	                Relative Time
    public final static char ShortTime = 't';
    public final static char LongTime = 'T';
    public final static char ShortDate = 'd';
    public final static char LongDate = 'D';
    public final static char ShortDateTime = 'f';
    public final static char LongDateTime = 'F';
    public final static char RelativeTime = 'R';
}
