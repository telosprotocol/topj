function init()
    create_key('temp')
    set_key('temp', '0')
end

function dofor()
    local num = 0
    for i=1, 10 do
        num = num + 1
    end
    set_key('temp', tostring(num))
end
