package com.example

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.dp
import com.example.model.QuestionBlock
import com.example.ui.screens.BlockCard
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.BlockProgressState
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel8, sdk = [36])
class GreetingScreenshotTest {

  @get:Rule val composeTestRule = createComposeRule()

  @Test
  fun greeting_screenshot() {
    val mockState = BlockProgressState(
        block = QuestionBlock(
            id = 1,
            name = "Mathematics: Core Algebra",
            description = "Master linear equation systems, quadratics, polynomials, and complex functions.",
            category = "Mathematics",
            startQuestionId = 1,
            endQuestionId = 25
        ),
        answeredCount = 12,
        correctCount = 10,
        percentCompleted = 48
    )

    composeTestRule.setContent {
      MyApplicationTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
          BlockCard(
              state = mockState,
              onClick = {}
          )
        }
      }
    }

    composeTestRule.waitForIdle()

    composeTestRule.onRoot().captureRoboImage(filePath = "src/test/screenshots/greeting.png")
  }
}
