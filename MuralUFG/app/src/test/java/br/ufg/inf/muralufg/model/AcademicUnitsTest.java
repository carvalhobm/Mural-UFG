package br.ufg.inf.muralufg.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Bruno on 28/02/2016.
 */
public class AcademicUnitsTest {

    AcademicUnits academicUnits = new AcademicUnits();

    @Before
    public void setUp() throws Exception {
        academicUnits = new AcademicUnits(1, "unit", 2);
    }

    @Test
    public void testGetId() throws Exception {
        academicUnits.setId(1);
        Assert.assertEquals(1, academicUnits.getId());
    }

    @Test
    public void testSetId() throws Exception {
        academicUnits.setId(2);
        Assert.assertEquals(2, academicUnits.getId());
    }

    @Test
    public void testGetUnitID() throws Exception {
        Assert.assertEquals(1,academicUnits.getUnitID() );
    }

    @Test
    public void testSetUnitID() throws Exception {
        academicUnits.setUnitID(3);
        Assert.assertEquals(3,academicUnits.getUnitID());
    }

    @Test
    public void testGetUnit() throws Exception {
        Assert.assertEquals("unit", academicUnits.getUnit());
    }

    @Test
    public void testSetUnit() throws Exception {
        academicUnits.setUnit("Academic Unit");
        Assert.assertEquals("Academic Unit", academicUnits.getUnit());
    }

    @Test
    public void testGetIsChecked() throws Exception {
        Assert.assertEquals(2, academicUnits.getIsChecked());
    }

    @Test
    public void testSetIsChecked() throws Exception {
        academicUnits.setIsChecked(4);
        Assert.assertEquals(4, academicUnits.getIsChecked());
    }
}