/**
 * Created by sand on 12/9/15.
 */


import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.widget.TextView;

import com.google.android.gms.example.bannerexample.MainActivityPage;
import com.google.android.gms.example.bannerexample.MainContentPage;
import com.google.android.gms.example.bannerexample.R;
import com.google.android.gms.example.bannerexample.com.example.constant.ArabicNormalizer;
import com.google.android.gms.example.bannerexample.com.example.constant.Constants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.FileOutputStream;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ApplicationTest extends ApplicationTestCase<Application> {


    private Constants object;
    public ApplicationTest() {
        super(Application.class);
    }


    @Test
    public void testExtractNumbers() {
        Constants object =new Constants(getContext());
        String s= " ذَلِكَ الْكِتَابُ لاَ رَيْبَ فِيهِ هُدًى لِّلْمُتَّقِينَ";
        assertEquals("output should be without tashkel", "ذَلِكَ الْكِتَابُ لاَ رَيْبَ فِيهِ هُدًى لِّلْمُتَّقِينَ", object.arabicNumaricCode(s));
    }

}
