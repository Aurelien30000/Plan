/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.java.main.java.com.djrapitops.plan;

import main.java.com.djrapitops.plan.Permissions;
import org.junit.Test;
import static org.junit.Assert.*;
import test.java.utils.MockUtils;

/**
 *
 * @author Risto
 */
public class PermissionsTest {
    
    /**
     *
     */
    public PermissionsTest() {
    }

    /**
     *
     */
    @Test
    public void testUserHasThisPermission() {
        assertTrue(Permissions.INSPECT_OTHER.userHasThisPermission(MockUtils.mockPlayer()));
    }

    /**
     *
     */
    @Test
    public void testGetPermission() {
        assertEquals("plan.inspect.other", Permissions.INSPECT_OTHER.getPermission());
    }
    
}