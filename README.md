# ActionMessage:
ActionMessage is a simple message sending plugin. 
When a player completes an action (such as breaking a block) they will recieve the corresponding message for that aciton.

## Defining Messages:
All messages are defined in the `actionmessage.conf` file located in `~/config/actionmessage.conf`. 
Below is a sample config:

```
messages {
    # Block Messages
    block {
        # Messages sent when a player breaks a block
        break {
            "minecraft:stone"="&9You broke stone!"
            "minecraft:stone[variant=andesite]"="&4You broke andesite specifically!"
        }
        # Messages sent when a player interacts with a block
        interact {
            # Mesages sent when a player primary interacts (left-clicks) a block
            primary {
                "minecraft:dirt"="&9You primary clicked on dirt!"
            }
            # Messages sent when a player secondary interacts (right-clicks) a block
            secondary {
                "minecraft:stone"="&9You secondary clicked on stone!"
            }
        }
        # Messages sent when a player places a block
        place {
            "minecraft:dirt"="&9You placed dirt!"
        }
    }
}
```

Messages are broken into different types (currently only block actions are supported). 
For each type there are different actions associated with it. All messages are in the form `"<ID>"="<Message>"` where `<ID>`
is either a block type ID such as `minecraft:stone` or a block state ID such as `minecraft:stone[variant=andersite]` and 
`<Message>` is the text you want the player to recieve on performing the specified action.  Block state IDs take precident, so
in the example above if a player were to break andesite he would **only** recieve the andesite specific message and not the 
generic stone one. Messages also support ampersand format codes. 

## Support Me
I will **never** charge money for the use of my plugins, however they do require a significant amount of work to maintain and update. If you'd like to show your support and buy me a cup of tea sometime (I don't drink that horrid coffee stuff :P) you can do so [here](https://www.paypal.me/zerthick)
