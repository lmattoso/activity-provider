package com.activity.provider.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class MultipleChoiceActivity extends Activity {
    private List<String> options;
}