/**
 * Every java.util.Date type will be adapted using the DateAdapter
 */
@XmlJavaTypeAdapter(value=DateAdapter.class, type=Date.class)
package com.mybuckstory.model.encodingcom;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
