package br.ufg.inf.muralufg.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Lucas Ferreira on 28/02/2016.
 */
public class DrawerItemTest {

    DrawerItem drawerItem;

    @Before
    public void setUp() throws Exception {
        drawerItem = new DrawerItem("itemName", 1);
    }

    @Test
    public void testGetItemName() throws Exception {
        Assert.assertEquals("itemName", drawerItem.getItemName());
    }

    @Test
    public void testGetImgResID() throws Exception {
        Assert.assertEquals(1, drawerItem.getImgResID());
    }
}