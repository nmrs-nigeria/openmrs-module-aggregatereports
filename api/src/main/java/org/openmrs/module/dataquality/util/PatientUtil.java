package org.openmrs.module.dataquality.util;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.openmrs.*;
import org.openmrs.annotation.Logging;
import org.openmrs.api.context.Context;
import sun.rmi.runtime.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import org.joda.time.DateTime;

public abstract class PatientUtil {
	
	public static Map<String, Integer> globalCache = new HashMap<>();//
        public static Map<String, DateTime> globalCacheTime = new HashMap<>();
        
	
}
